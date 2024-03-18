import {
	Paper,
	TextInput,
	ScrollArea,
	createTheme,
	LoadingOverlay,
} from '@mantine/core';

export default createTheme({
	components: {
		TextInput: TextInput.extend({
			defaultProps: {
				spellCheck: false,
			},
		}),
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
