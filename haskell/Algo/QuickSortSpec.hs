module Algo.QuickSortSpec where

import Algo.QuickSort (qsort)
import Test.Hspec (Spec, describe, it, shouldBe)

spec :: Spec
spec = do
	describe "Quick sort" $ do
		it "sorts list" $ do
			qsort [3, 9, 7, 2, 1] `shouldBe` [1, 2, 3, 7, 9]

		it "keeps emty list" $ do
			qsort ([] :: [Integer]) `shouldBe` []
