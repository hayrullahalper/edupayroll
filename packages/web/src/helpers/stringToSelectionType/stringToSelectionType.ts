export default function stringToSelectionType<T extends string>(
	str: T,
): 'INCLUDE' | 'EXCLUDE' {
	switch (str) {
		case 'include':
			return 'INCLUDE';
		case 'exclude':
			return 'EXCLUDE';
		default:
			throw new Error('Invalid selection type');
	}
}
