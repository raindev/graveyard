struct DNA {
  let nucleotides: [String: Int]

  init?(strand: String) {
    var nucleotides = ["C": 0, "G": 0, "A": 0, "T": 0]
    for symbol in strand {
      if let count = nucleotides[String(symbol)] {
        nucleotides[String(symbol)] = count + 1
      } else {
        return nil
      }
    }
    self.nucleotides = nucleotides
  }

  func count(_ nucleotide: String) -> Int? {
    nucleotides[nucleotide]
  }

  func counts() -> [String: Int] {
    nucleotides
  }

}
