import 'dotenv-flow/config';
import { execSync } from 'child_process';
import { readFileSync, writeFileSync, readdirSync, mkdirSync } from 'fs';

const apiDestination = 'src/api';

// remove api

execSync(`rimraf ${apiDestination}`, {
	stdio: 'inherit'
});

// generate api

execSync(`npx @openapitools/openapi-generator-cli generate -s -i ${process.env.REST_API_SPEC_URL} -g typescript-fetch -o ${apiDestination}`, {
	stdio: 'inherit'
});

// fix runtime.ts

const runtimePath = `${apiDestination}/runtime.ts`;

let runtime = readFileSync(runtimePath, 'utf8');

const target = `const response = await this.fetchApi(url, init);
        if (response && (response.status >= 200 && response.status < 300)) {
            return response;
        }
        throw new ResponseError(response, 'Response returned an error code');`;

const replacement = `return await this.fetchApi(url, init);`;

runtime = runtime.replace(target, replacement);

writeFileSync(runtimePath, runtime);

// read apis

const apis = readdirSync(`${apiDestination}/apis`)
	.filter((file) => file !== 'index.ts');

// generate controllers

const controllers = apis.map((api) => {
	return api.replace('.ts', '')
		.replace(/Api$/, '')
		.replace(/Controller$/, '')
		.replace(/([a-z0-9])([A-Z])/g, '$1-$2')
		.toLowerCase();
});

// generate client folder and files

mkdirSync(`${apiDestination}/client`);

writeFileSync(`${apiDestination}/client/index.ts`, `export { default } from './client';`);

let client = 'import { Configuration } from \'../runtime\';\nimport {';

apis.forEach((api, index) => {
	client += ` ${api.replace('.ts', '')}${index < apis.length - 1 ? ',' : ' '}`;
});

client += `} from '../apis';\n\n`;

client += `type Controller = '${controllers.join(`' | '`)}';\n\n`;

client += `type ControllerAPI<T extends Controller> =\n`;

controllers.forEach((controller, index) => {
	client += `\tT extends '${controller}' ? ${apis[index].replace('.ts', '')} :\n`;
});

client += `\tnever;\n\n`;

client += `const config = new Configuration({\n`;
client += `\tbasePath: import.meta.env.VITE_API_BASE_PATH\n`;
client += `});\n\n`;

client += `function client<T extends Controller>(controller: T): ControllerAPI<T> {\n`;
client += `\tswitch (controller) {\n`;

controllers.forEach((controller, index) => {
	client += `\tcase '${controller}':\n`;
	client += `\t\treturn new ${apis[index].replace('.ts', '')}(config) as any;\n`;
});

client += `\tdefault:\n`;
client += `\t\tthrow new Error(\`Unknown controller: \${controller}\`);\n`;
client += `\t}\n`;
client += `}\n\n`;

client += `export default client;\n`;

writeFileSync(`${apiDestination}/client/client.ts`, client);

// add client to index.ts

let index = readFileSync(`${apiDestination}/index.ts`, 'utf8');

index += `export { default as client } from './client';\n`;

writeFileSync(`${apiDestination}/index.ts`, index);


