export default function generateKey(obj?: object | null) {
	if (!obj) {
		return '';
	}

	return Object.keys(obj)
		.sort((a, b) => a.localeCompare(b))
		.map((key) => `${key}:${obj[key as keyof typeof obj]}`)
		.join('::');
}
