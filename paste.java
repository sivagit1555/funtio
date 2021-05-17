

METHOD RndIntHelperNonPowerOfTwo(maxInclusive)
  if maxInclusive <= MODULUS - 1:
    // NOTE: If the programming language implements
    // division with two integers by discarding the result's
    // fractional part, the division can be used as is without
    // using a "floor" function.
    nPlusOne = maxInclusive + 1
    maxexc = floor((MODULUS - 1) / nPlusOne) * nPlusOne
    while true // until we return a value
      ret = NEXTRAND()
      if ret < nPlusOne: return ret
      if ret < maxexc: return rem(ret, nPlusOne)
    end
  else
    cx = floor(maxInclusive / MODULUS) + 1
    while true // until we return a value
       ret = cx * NEXTRAND()
       // NOTE: The addition operation below should
       // check for integer overflow and should reject the
       // number if overflow would result.
       ret = ret + RNDINT(cx - 1)
       if ret <= maxInclusive: return ret
    end
  end
END METHOD

METHOD RndIntHelperPowerOfTwo(maxInclusive)
  // NOTE: Finds the number of bits minus 1 needed
  // to represent MODULUS (in other words, the number
  // of random bits returned by NEXTRAND() ). This will
  // be a constant here, though.
  modBits = ln(MODULUS)/ln(2)
  // Lumbroso's Fast Dice Roller.
  x = 1
  y = 0
  nextBit = modBits
  rngv = 0
  while true // until we return a value
    if nextBit >= modBits
      nextBit = 0
      rngv = NEXTRAND()
    end
    x = x * 2
    y = y * 2 + rem(rngv, 2)
    rngv = floor(rngv / 2)
    nextBit = nextBit + 1
    if x > maxInclusive
      if y <= maxInclusive: return y
      x = x - maxInclusive - 1
      y = y - maxInclusive - 1
    end
  end
END METHOD

METHOD RNDINT(maxInclusive)
  // maxInclusive must be 0 or greater
  if maxInclusive < 0: return error
  if maxInclusive == 0: return 0
  if maxInclusive == MODULUS - 1: return NEXTRAND()
  // NOTE: Finds the number of bits minus 1 needed
  // to represent MODULUS (if it's a power of 2).
  // This will be a constant here, though.
  modBits=ln(MODULUS)/ln(2)
  if floor(modBits) == modBits // Is an integer
    return RndIntHelperPowerOfTwo(maxInclusive)
  else
    return RndIntHelperNonPowerOfTwo(maxInclusive)
  end
END METHOD
