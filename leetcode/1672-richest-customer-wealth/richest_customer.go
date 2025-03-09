package richestcustomerwealth

func maximumWealth(accounts [][]int) int {
	maxWealth := sum(accounts[0])
	for _, customerAccounts := range accounts[1:] {
		customerWealth := sum(customerAccounts)
		if customerWealth > maxWealth {
			maxWealth = customerWealth
		}
	}
	return maxWealth
}

func sum(numbers []int) (sum int) {
	for _, n := range numbers {
		sum += n
	}
	return
}
