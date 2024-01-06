#include <windows.h>
#include <iostream>
//#include <process.h>
#include <vector>
volatile UINT sum;//to indicate that the value of a variable can change outside the current execution flow
volatile DWORD dwCount;
DWORD WINAPI Worker(LPVOID Mas)
{
	//sum = 0;
	std::vector<long>* m = reinterpret_cast<std::vector<long>*>(Mas);
	for (int i = 0; i < m->size(); i++)
	{
		sum += (*m)[i] * (*m)[i];
		std::cout << "Intermediate result: " << sum << std::endl;
		Sleep(200);
	}
	return 0;
}

int main()
{
	srand((int)time(NULL));
	int n;
	std::cout << "Enter size of array: ";
	std::cin >> n;
	if (n <= 0) {
		std::cout << "Error: uncorrect data";
	}
	else {
		std::vector<long> mas(n);
		for (int i = 0; i < n; i++)
			mas[i] = rand() % 10;
		for (int i = 0; i < n; i++)
			std::cout << mas[i] << " ";

		HANDLE	hThread;
		DWORD	IDThread;
		//UINT	IDThread;
		char str;


		hThread = CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)Worker, (LPVOID)&mas, 0, &IDThread);
		/*hThread = (HANDLE)
			_beginthreadex(NULL, 0, (LPTHREAD_START_ROUTINE)Worker, &mas, 0, &IDThread);*/
		if (hThread == NULL)
			return GetLastError();
		for (; ; )
		{
			std::cout << std::endl << "Choose :" << std::endl;
			std::cout << "\t'1' - show result" << std::endl;
			std::cout << "\t'2' - suspend thread" << std::endl;
			std::cout << "\t'3' - resume thread" << std::endl;
			std::cout << "\t'4' - exit" << std::endl;
			std::cin >> str; //another number is ignored
			switch (str)
			{
			case '1':

				std::cout << "sum = " << sum << std::endl;
				break;
			case '2':

				dwCount = SuspendThread(hThread);
				std::cout << "Thread suspend count = " << dwCount << std::endl;
				break;
			case '3':

				dwCount = ResumeThread(hThread);
				std::cout << "Thread suspend count = " << dwCount << std::endl;
				break;
			case '4':
				CloseHandle(hThread);
				return 0;
			}
		}
		//WaitForSingleObject(hThread, INFINITE);
		CloseHandle(hThread);
	}

	return 0;
}
