module Main (main) where

import Algo.Print
import Test.Hspec

main :: IO ()
main = hspec $ do

  describe "Validate haqify function" $ do
    it "haqify is supposed to prefix Haq! to things" $ do
      haqify "me" `shouldBe` "Haq! me"
