import type { PropsWithChildren } from 'react';
import { useDisclosure } from '@mantine/hooks';
import { useMemo, useRef } from 'react';

import DocumentsContext from './DocumentsContext';

type DocumentsProviderProps = PropsWithChildren<any>;

export default function DocumentsProvider({
	children,
}: DocumentsProviderProps) {
	const targetRef = useRef<string | null>(null);
	const targetsRef = useRef<string[] | null>(null);

	const [createModalVisible, createModalDisclosure] = useDisclosure();
	const [updateModalVisible, updateModalDisclosure] = useDisclosure();
	const [removeModalVisible, removeModalDisclosure] = useDisclosure();

	const contextValue = useMemo(
		() => ({
			create: () => createModalDisclosure.open(),
			update: (id: string) => {
				targetRef.current = id;
				updateModalDisclosure.open();
			},
			remove: (ids: string[]) => {
				targetsRef.current = ids;
				removeModalDisclosure.open();
			},
		}),
		[createModalDisclosure, removeModalDisclosure, updateModalDisclosure],
	);

	return (
		<>
			<DocumentsContext.Provider value={contextValue}>
				{children}
			</DocumentsContext.Provider>

			{createModalVisible && <div>create</div>}
			{updateModalVisible && !!targetRef.current && <div>update</div>}
			{removeModalVisible && !!targetsRef.current && <div>remove</div>}
		</>
	);
}
