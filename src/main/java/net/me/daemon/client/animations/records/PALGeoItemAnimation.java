package net.me.daemon.client.animations.records;






// will be used to store PAL and Geckolib animations together
// only callable inside an AnimatableWeapon since it calls the animation handler for it
// this is just a record
// Solution1: it will only store an identifier to a json file that stores the paths to the animations
// Solution2: have it store the identifiers for both geckolib and PAL (probably better imo)
public record PALGeoItemAnimation() {
}
