import Cookies from 'js-cookie';
import paths from '../../routes/paths';
import { Configuration, FetchParams, RequestContext, ResponseContext } from '../runtime';
import { AuthControllerApi, DocumentControllerApi, ExportControllerApi, RecordControllerApi, SchoolControllerApi, TeacherControllerApi, UserControllerApi } from '../apis';

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

const client = {
	auth: new AuthControllerApi(config),
	document: new DocumentControllerApi(config),
	export: new ExportControllerApi(config),
	record: new RecordControllerApi(config),
	school: new SchoolControllerApi(config),
	teacher: new TeacherControllerApi(config),
	user: new UserControllerApi(config)
};

export default client;
