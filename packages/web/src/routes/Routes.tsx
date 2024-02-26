import { Route, Routes as BaseRoutes } from 'react-router-dom';
import paths from './paths.ts';
import Panel from './Panel';

export default function Routes() {
	return (
		<BaseRoutes>
			<Route path={paths.panel} element={<Panel />} />
		</BaseRoutes>
	)
}
