import {
	Paper,
	TextInput,
	ScrollArea,
	createTheme,
	LoadingOverlay,
	PasswordInput,
} from '@mantine/core';
import { IconEye, IconEyeOff } from '@tabler/icons-react';

const light = createTheme({
	primaryColor: 'indigo',
	components: {
		TextInput: TextInput.extend({
			defaultProps: {
				spellCheck: false,
			},
		}),
		Paper: Paper.extend({
			defaultProps: {
				bg: 'gray.0',
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
		PasswordInput: PasswordInput.extend({
			defaultProps: {
				visibilityToggleIcon: ({ reveal }) =>
					reveal ? (
						<IconEyeOff size={16} stroke={1.5} />
					) : (
						<IconEye size={16} stroke={1.5} />
					),
			},
		}),
	},
});

export default light;
