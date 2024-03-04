import { ReactNode } from 'react';
import {
	IconBooks,
	IconUsers,
	IconSettings,
	IconTableExport,
	TablerIconsProps,
} from '@tabler/icons-react';

import paths from '../../routes/paths';

type NavLink = {
	to: string;
	icon: (props: TablerIconsProps) => ReactNode;
	label: string;
	relations?: string[];
};

export const links: NavLink[] = [
	{
		to: paths.documents,
		icon: IconBooks,
		label: 'Belgeler',
		relations: [paths.document],
	},
	{
		to: paths.teachers,
		icon: IconUsers,
		label: 'Öğretmenler',
		relations: [paths.teacher],
	},
	{
		to: paths.exports,
		icon: IconTableExport,
		label: 'Kaydedilenler',
	},
	{
		to: paths.settings,
		icon: IconSettings,
		label: 'Seçenekler',
		relations: [paths.school, paths.profile, paths.changePassword],
	},
];
