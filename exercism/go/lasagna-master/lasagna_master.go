package lasagna

func PreparationTime(layers []string, layerTime int) int {
	if layerTime == 0 {
		layerTime = 2
	}
	return len(layers) * layerTime
}

func Quantities(layers []string) (noodles int, sauce float64) {
	for _, layer := range layers {
		switch layer {
		case "noodles":
			noodles += 50
		case "sauce":
			sauce += .2
		}
	}
	return noodles, sauce
}

func AddSecretIngredient(friendsList, myList []string) {
	myList[len(myList)-1] = friendsList[len(friendsList)-1]
}

func ScaleRecipe(recipe []float64, portions int) []float64 {
	ingredients := make([]float64, len(recipe))
	scale := float64(portions) / 2
	for i := range recipe {
		ingredients[i] = recipe[i] * scale
	}
	return ingredients
}
