#include <iostream>
#include <windows.h>
#include <string>

HANDLE eventA, eventB, EndParent;

int main(int argc, char* argv[])
{
	eventA = CreateEvent(NULL, FALSE, FALSE, L"eventA");
	if (eventA == NULL)
		return GetLastError();
	eventB = CreateEvent(NULL, FALSE, FALSE, L"eventB");
	if (eventB == NULL)
			return GetLastError();
	EndParent = CreateEvent(NULL, FALSE, FALSE, L"EndParent");
	if (EndParent == NULL)
			return GetLastError();

	HANDLE hMutex;
	hMutex = OpenMutex(SYNCHRONIZE, FALSE, L"Mutex");

	int AmountMess = atoi(argv[argc - 1]);//copy the last command line argument in integer

	if (hMutex == NULL) {
		std::cout << "Create mutex failed." << std::endl;
		return GetLastError();
	}

	WaitForSingleObject(hMutex, INFINITE);

	for (int i = 0; i < AmountMess; i++) {
		std::cout << "Enter the message: ";
		char mess;
		std::cin >> mess;
		std::cout << std::endl;

		if (mess == 'A')
			SetEvent(eventA);
		if (mess == 'B')
			SetEvent(eventB);

		SetEvent(EndParent);
	}

	ReleaseMutex(hMutex);

	CloseHandle(EndParent);
	CloseHandle(eventB);
	CloseHandle(eventA);

	return 0;
}
