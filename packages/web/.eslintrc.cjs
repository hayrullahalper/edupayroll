module.exports = {
	root: true,
	env: {
		es2020: true,
		browser: true
	},
	extends: [
		'airbnb',
		'prettier',
		'airbnb/hooks',
		'airbnb-typescript',
		'eslint:recommended',
		'plugin:react/recommended',
		'plugin:import/recommended',
		'plugin:jsx-a11y/recommended',
		'plugin:react-hooks/recommended'
	],
	parser: '@typescript-eslint/parser',
	ignorePatterns: ['dist', '.eslintrc.cjs'],
	parserOptions: {
		ecmaVersion: 'latest',
		sourceType: 'module',
		project: ['./tsconfig.json']
	},
	overrides: [
		{
			files: ['*.d.ts'],
			rules: {
				'no-unused-vars': 'off',
				'@typescript-eslint/no-unused-vars': 'off'
			}
		}
	],
	plugins: [
		'react',
		'prettier',
		'react-refresh'
	],
	rules: {
		'no-console': 'error',
		'consistent-return': 'off',
		'prettier/prettier': 'error',
		'no-restricted-exports': 'off',
		'react/react-in-jsx-scope': 'off',
		'import/prefer-default-export': 'off',
		'react/jsx-props-no-spreading': 'off',
		'@typescript-eslint/indent': ['error', 'tab'],
		'react-refresh/only-export-components': [
			'warn',
			{ allowConstantExport: true }
		],
		'import/order': ['error', {
			'groups': ['builtin', 'external', 'internal', 'parent', 'sibling', 'index'],
			'newlines-between': 'always'
		}]
	}
};
