import Cookies from 'js-cookie';
import paths from '../../routes/paths';
import { Configuration, FetchParams, RequestContext, ResponseContext } from '../runtime';
import { AuthControllerApi, SchoolControllerApi, TeacherControllerApi, UserControllerApi } from '../apis';

type Controller = 'auth' | 'school' | 'teacher' | 'user';

type ControllerAPI<T extends Controller> =
	T extends 'auth' ? AuthControllerApi :
	T extends 'school' ? SchoolControllerApi :
	T extends 'teacher' ? TeacherControllerApi :
	T extends 'user' ? UserControllerApi :
	never;

const config = new Configuration({
	middleware: [{
		pre(context: RequestContext): Promise<FetchParams | void> {
			const token = Cookies.get('access_token');

			if (!!context.init.headers && !!token) {
				context.init.headers = {
					...context.init.headers,
					Authorization: `Bearer ${token}`,
				};
			}

			return Promise.resolve(context);
		},
		post(context: ResponseContext): Promise<Response | void> {
			if (context.response.status === 401 && !context.response.url.includes('auth')) {
				Cookies.remove('access_token');
				window.location.assign(paths.login);
			}

			return Promise.resolve(context.response);
		}
	}],
	basePath: import.meta.env.VITE_REST_API_BASE_URL
});

function client<T extends Controller>(controller: T): ControllerAPI<T> {
	switch (controller) {
	case 'auth':
		return new AuthControllerApi(config) as any;
	case 'school':
		return new SchoolControllerApi(config) as any;
	case 'teacher':
		return new TeacherControllerApi(config) as any;
	case 'user':
		return new UserControllerApi(config) as any;
	default:
		throw new Error(`Unknown controller: ${controller}`);
	}
}

export default client;
