import { createTheme, LoadingOverlay, Paper, ScrollArea } from '@mantine/core';

export default createTheme({
	components: {
		Paper: Paper.extend({
			defaultProps: {
				bg: 'gray.2',
				radius: 'md',
			},
		}),
		ScrollArea: ScrollArea.extend({
			defaultProps: {
				scrollbarSize: 8,
			},
		}),
		LoadingOverlay: LoadingOverlay.extend({
			defaultProps: {
				visible: true,
				loaderProps: {
					size: 'md',
					color: 'indigo',
				},
			},
		}),
	},
});
