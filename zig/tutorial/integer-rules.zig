const expect = @import("std").testing.expect;

test "integer widening" {
    const a: u8 = 250;
    const b: u16 = a;
    const c: u32 = b;
    try expect(c == a);
}

test "@intCast" {
    const x: u64 = 200;
    const y = @as(u8, @intCast(x));
    try expect(@TypeOf(y) == u8);
}

test "well defined overflow" {
    var a: u8 = 255;
    a +%= 1;
    try expect(a == 0);
}
