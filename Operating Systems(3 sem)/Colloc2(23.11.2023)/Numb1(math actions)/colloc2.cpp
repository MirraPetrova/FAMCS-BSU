#include <iostream>
#include <windows.h>
#include <fstream>
#include <string>
#include <vector>

std::string Address;
double SumNumbers = 0;

double CalcOperations(int NumbOperation, std::vector<double> Numbers) {
	if (NumbOperation == 1) {
		double sum = 0;
		for (int i = 0; i < Numbers.size(); i++)
			sum += Numbers[i];
		return sum;
	}
	if (NumbOperation == 2) {
		double mult = 1;
		for (int i = 0; i < Numbers.size(); i++)
			mult *= Numbers[i];
		return mult;
	}
	if (NumbOperation == 3) {
		double Sum_square = 0;
		for (int i = 0; i < Numbers.size(); i++)
			Sum_square += Numbers[i] * Numbers[i];
		return Sum_square;
	}
	else
		throw("Error: uncorrect data");
}

void InputsThreads(int k) {
	std::ifstream read_from(Address + "\\in_" + std::to_string(k + 1) + ".dat", std::ios::in);
	int NumbOperation;
	read_from >> NumbOperation;
	std::vector <double> Numbers;
	double n;
	while (read_from >> n) {
		Numbers.push_back(n);
	}
	read_from.close();
	SumNumbers += CalcOperations(NumbOperation, Numbers);
}

int main(int argc, char* argv[]) {
	int LotOfThreads;
	std::cout << "Enter count of threads: ";
	//LotOfThreads = atoi(argv[1]);
	std::cin >> LotOfThreads;
	std::cout << "Enter address of files: ";
	//Address = argv[2];
	std::cin >> Address;

	HANDLE* hThread = new HANDLE[LotOfThreads];
	DWORD* IDThread = new DWORD[LotOfThreads];

	for (int i = 0; i < LotOfThreads; i++) {
		hThread[i] = CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)InputsThreads, (LPVOID)i, 0, &IDThread[i]);
		/*if (hThread[i] != CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)InputsThreads, (LPVOID)i, 0, &IDThread[i]))
			return GetLastError();*/
	}

	WaitForMultipleObjects(LotOfThreads, hThread, TRUE, INFINITE);

	std::ofstream write_in(Address + "\\out.dat", std::ios::out);
	write_in << SumNumbers;
	write_in.close();

	for (int i = 1; i < LotOfThreads; i++) {
		CloseHandle(hThread[i]);
	}

	delete[] IDThread;
	delete[] hThread;

	return 0;
}