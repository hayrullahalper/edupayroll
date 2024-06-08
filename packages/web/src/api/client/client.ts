import { Configuration } from '../runtime';
import { AuthControllerApi, SchoolControllerApi, TeacherControllerApi } from '../apis';

type Controller = 'auth' | 'school' | 'teacher';

type ControllerAPI<T extends Controller> =
	T extends 'auth' ? AuthControllerApi :
	T extends 'school' ? SchoolControllerApi :
	T extends 'teacher' ? TeacherControllerApi :
	never;

const config = new Configuration({
	basePath: import.meta.env.VITE_API_BASE_PATH
});

function client<T extends Controller>(controller: T): ControllerAPI<T> {
	switch (controller) {
	case 'auth':
		return new AuthControllerApi(config) as any;
	case 'school':
		return new SchoolControllerApi(config) as any;
	case 'teacher':
		return new TeacherControllerApi(config) as any;
	default:
		throw new Error(`Unknown controller: ${controller}`);
	}
}

export default client;
