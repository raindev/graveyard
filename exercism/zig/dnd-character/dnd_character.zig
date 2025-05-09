const std = @import("std");
const mem = std.mem;
const posix = std.posix;
const Random = std.Random;

var rngInitialized = false;
var prng: Random.DefaultPrng = Random.DefaultPrng.init(getSeed());
var random: Random = undefined;

fn getSeed() u64 {
    var seed: u64 = undefined;
    posix.getrandom(mem.asBytes(&seed)) catch |err| @panic(@errorName(err));
}

pub fn modifier(score: i8) i8 {
    return @divFloor(score - 10, 2);
}

pub fn ability() i8 {
    if (!rngInitialized) {
        var seed: u64 = undefined;
        posix.getrandom(mem.asBytes(&seed)) catch |err| @panic(@errorName(err));
        prng = Random.DefaultPrng.init(seed);
        // Random holds a pointer to Prng, which therefore cannot be local
        random = prng.random();
    }
    var sum: i8 = 0;
    var min: i8 = 6;
    for (0..4) |_| {
        const roll = random.intRangeAtMost(i8, 1, 6);
        sum += roll;
        min = @min(min, roll);
    }
    return sum - min;
}

pub const Character = struct {
    strength: i8,
    dexterity: i8,
    constitution: i8,
    intelligence: i8,
    wisdom: i8,
    charisma: i8,
    hitpoints: i8,

    pub fn init() @This() {
        var character: @This() = undefined;
        inline for (@typeInfo(@This()).Struct.fields) |f| {
            @field(character, f.name) = ability();
        }
        character.hitpoints = 10 + modifier(character.constitution);
        return character;
    }
};
