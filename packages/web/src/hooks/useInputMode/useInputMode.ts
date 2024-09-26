import type { TextInputProps } from '@mantine/core';
import type { Dispatch, SetStateAction } from 'react';
import { useMemo, useState } from 'react';

export type InputMode = 'view' | 'edit';

type UseInputModeReturn = readonly [
	mode: InputMode,
	setMode: Dispatch<SetStateAction<InputMode>>,
	inputProps: Pick<TextInputProps, 'readOnly' | 'variant'>,
];

export default function useInputMode(): UseInputModeReturn {
	const [mode, setMode] = useState<InputMode>('view');

	const inputProps = useMemo(
		() => ({
			readOnly: mode === 'view',
			variant: mode === 'view' ? 'filled' : 'default',
		}),
		[mode],
	);

	return [mode, setMode, inputProps] as const;
}
