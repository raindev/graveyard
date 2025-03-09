package richestcustomerwealth

func maximumWealth(accounts [][]int) int {
	maxWealth := 0
	for _, customerAccounts := range accounts {
		customerWealth := 0
		for _, balance := range customerAccounts {
			customerWealth += balance
		}
		if customerWealth > maxWealth {
			maxWealth = customerWealth
		}
	}
	return maxWealth
}
