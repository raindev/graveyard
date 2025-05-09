package cipher

import (
	"crypto/rand"
	"math/big"
	"regexp"
)

type vigenere string
type shift byte

func NewCaesar() Cipher {
	return NewShift(3)
}

func NewShift(distance int) Cipher {
	if distance == 0 || distance >= 26 || distance <= -26 {
		return nil
	}
	return shift(distance)
}

func RandomShift() Cipher {
	return shift(randShift())
}

func randShift() int {
	i, err := rand.Int(rand.Reader, big.NewInt(25))
	if err != nil {
		panic(err)
	}
	return int(i.Int64()) + 1 // normalize [0, 24] => [1, 25]
}

func NewVigenere(key string) Cipher {
	if regexp.MustCompile("^$|(^a+$)|[^a-z]").MatchString(key) {
		return nil
	}
	return vigenere(key)
}

func RandomVigenere() Cipher {
	buff := [26]byte{}
	for i := range buff {
		buff[i] = 'a' + byte(randShift())
	}
	return vigenere(string(buff[:]))
}

func (c shift) Encode(input string) string {
	return encode(input, string('a'+c), false)
}

func (c shift) Decode(input string) string {
	return encode(input, string('a'+c), true)
}

func (v vigenere) Encode(input string) string {
	return encode(input, string(v), false)
}

func (v vigenere) Decode(input string) string {
	return encode(input, string(v), true)
}

func encode(input, key string, invert bool) string {
	res := make([]byte, 0, len(input))
	index := 0
	for i := range input {
		c := input[i]
		if c >= 'A' && c <= 'Z' {
			c += 'a' - 'A'
		}
		if c >= 'a' && c <= 'z' {
			offset := key[index%len(key)] - 'a'
			if invert {
				offset = -offset
			}
			encodedChar := 'a' + ((c-'a'+offset)+26)%26
			res = append(res, encodedChar)
			index++
		}
	}
	return string(res)
}
