import { execSync } from 'node:child_process';
import { mkdirSync, readdirSync, readFileSync, statSync, writeFileSync } from 'node:fs';
import { join } from 'node:path';
import 'dotenv-flow/config';

const apiDestination = 'src/api';

// remove api

execSync(`rimraf ${apiDestination}`, {
	stdio: 'inherit',
});

// generate api

execSync(`npx @openapitools/openapi-generator-cli generate -s -i ${process.env.REST_API_SPEC_URL} -g typescript-fetch -o ${apiDestination}`, {
	stdio: 'inherit',
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
	.filter(file => file !== 'index.ts');

// generate client folder and files

mkdirSync(`${apiDestination}/client`);

writeFileSync(`${apiDestination}/client/index.ts`, `export { default } from './client';`);

let client = 'import Cookies from \'js-cookie\';\n';
client += 'import paths from \'../../routes/paths\';\n';

client += 'import { Configuration, FetchParams, RequestContext, ResponseContext } from \'../runtime\';\nimport {';

apis.forEach((api, index) => {
	client += ` ${api.replace('.ts', '')}${index < apis.length - 1 ? ',' : ' '}`;
});

client += `} from '../apis';\n\n`;

client += `const config = new Configuration({\n`;
client += `\tmiddleware: [{\n`;
client += `\t\tpre(context: RequestContext): Promise<FetchParams | void> {\n`;
client += `\t\t\tconst token = Cookies.get('access_token');\n\n`;
client += `\t\t\tif (!!context.init.headers && !!token) {\n`;
client += `\t\t\t\tcontext.init.headers = {\n`;
client += `\t\t\t\t\t...context.init.headers,\n`;
client += `\t\t\t\t\tAuthorization: \`Bearer \${token}\`,\n`;
client += `\t\t\t\t};\n`;
client += `\t\t\t}\n\n`;
client += `\t\t\treturn Promise.resolve(context);\n`;
client += `\t\t},\n`;
client += `\t\tpost(context: ResponseContext): Promise<Response | void> {\n`;
client += `\t\t\tif (context.response.status === 401 && !context.response.url.includes('auth')) {\n`;
client += `\t\t\t\tCookies.remove('access_token');\n`;
client += `\t\t\t\twindow.location.assign(paths.login);\n`;
client += `\t\t\t}\n\n`;
client += `\t\t\treturn Promise.resolve(context.response);\n`;
client += `\t\t}\n`;
client += `\t}],\n`;
client += `\tbasePath: import.meta.env.VITE_REST_API_BASE_URL\n`;
client += `});\n\n`;

client += `const client = {\n`;

apis.forEach((api, index) => {
	let name = api.replace('.ts', '').replace(/Api$/, '');
	name = name.replace('Controller', '').toLowerCase();

	client += `\t${name}: new ${api.replace('.ts', '')}(config)${index < apis.length - 1 ? ',' : ''}\n`;
});

client += `};\n\n`;

client += `export default client;\n`;

writeFileSync(`${apiDestination}/client/client.ts`, client);

// add client to index.ts

let index = readFileSync(`${apiDestination}/index.ts`, 'utf8');

index += `export { default as client } from './client';\n`;

writeFileSync(`${apiDestination}/index.ts`, index);

// fix useless headers

function discover(dir, list = []) {
	const files = readdirSync(dir);

	files.forEach((file) => {
		const path = join(dir, file);
		const stat = statSync(path);

		if (stat.isDirectory()) {
			discover(path, list);
			return;
		}

		if (path.endsWith('.ts')) {
			list.push(path);
		}
	});

	return list;
}

const files = discover(apiDestination);

const USEFUL_HEADER = '// @ts-nocheck\n\n';
const USELESS_HEADER = '/* tslint:disable */\n/* eslint-disable */\n';

files.forEach((file) => {
	let content = readFileSync(file, 'utf8');

	if (content.startsWith(USELESS_HEADER)) {
		content = content.replace(USELESS_HEADER, USEFUL_HEADER);
		writeFileSync(file, content);
	}
});
