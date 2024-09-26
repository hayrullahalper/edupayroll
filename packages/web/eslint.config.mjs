import antfu from '@antfu/eslint-config';
import tanstackQuery from '@tanstack/eslint-plugin-query';

export default antfu({
	stylistic: {
		semi: true,
		maxLen: 100,
		indent: 'tab',
	},

	react: true,
	typescript: true,

	rules: {
		'no-console': 'warn',
		'style/indent': ['error', 'tab'],
		'react/no-unstable-default-props': 'off',
		'unused-imports/no-unused-vars': ['error', {
			vars: 'all',
			args: 'after-used',
			ignoreRestSiblings: false,
			argsIgnorePattern: '^_',
			caughtErrors: 'none',
		}],
	},

	ignores: [
		'dist',
		'build',
		'*.d.ts',
		'src/api',
		'node_modules',
		'vite.config.ts',
	],
}, {
	plugins: {
		'@tanstack/query': tanstackQuery,
	},
});
