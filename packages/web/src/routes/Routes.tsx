import { Route, Routes as BaseRoutes } from 'react-router-dom';

import Panel from './Panel';
import paths from './paths';

export default function Routes() {
	return (
		<BaseRoutes>
			<Route path={paths.panel} element={<Panel />} />
		</BaseRoutes>
	);
}
