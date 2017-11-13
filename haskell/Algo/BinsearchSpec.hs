module Main (main) where

import Algo.Binsearch
import Test.Hspec

main :: IO ()
main = hspec $ do

  describe "Binary search" $ do
    it "finds nothing in empty list" $ do
      binarySearch [] 9 `shouldBe` Nothing

    it "finds element in singleton list" $ do
      binarySearch [3] 3 `shouldBe` Just 0

    it "find element in list" $ do
      binarySearch [1, 2, 3, 4, 5] 4 `shouldBe` Just 3

    it "finds tail element in list" $ do
      binarySearch [2, 3, 7, 9] 9 `shouldBe` Just 3

    it "finds head element in list" $ do
      binarySearch [2, 3, 7, 9] 2 `shouldBe` Just 0
