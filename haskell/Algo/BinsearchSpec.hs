module Main (main) where

import Algo.Binsearch
import Data.Vector (empty, singleton, fromList)
import Test.Hspec

main :: IO ()
main = hspec $ do

  describe "Binary search" $ do
    it "finds nothing in empty list" $ do
      binarySearch empty 9 `shouldBe` Nothing

    it "finds element in singleton list" $ do
      binarySearch (singleton 3) 3 `shouldBe` Just 0

    it "find element in list" $ do
      binarySearch (fromList [1, 2, 3, 4, 5]) 4 `shouldBe` Just 3

    it "finds tail element in list" $ do
      binarySearch (fromList [2, 3, 7, 9]) 9 `shouldBe` Just 3

    it "finds head element in list" $ do
      binarySearch (fromList [2, 3, 7, 9]) 2 `shouldBe` Just 0
