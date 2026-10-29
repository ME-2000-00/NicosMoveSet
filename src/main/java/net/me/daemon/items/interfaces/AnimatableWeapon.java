package net.me.daemon.items.interfaces;







// the interface that enables the dev (aka just me rn :3) to more easily use PAL and geckolib in an item together
// it doesnt handle the registries it just makes sure there is a connection between PAL, geckolib and a custom handler
// that actually handles the animations, like starting, stopping and syncing them
// the interface will probably have helper functions to call the new kind of animation which is just a collective of the geckolib and PAl animation together
// i mean using animations by ID might just be a good idea, not by raw animation like it normally happens in geckolib and PAl



// HANDLER:
// so the handler will have a hash map im guessing that stores the records
// registering will make the raw animations needed, but i still need a way to link them together with a reference to them or smth like that idfk
// the registry then gives back an identifier that can be used to call these animations
// calling an animation will need the data that PAL and Geckolib needs
// also it should have checks for server side calling and client side calling
// bruh ts will take up a lot of time but tbh id rather do this then have PAL trigger Geckolib, plus it makes animating easier
// i just need the handler for syncing them up and having it all stored in the same space then litteraly everywhere

// like when all this animation bs is done i should turn it into a library for future usage, same with the ability stuff probably
// so its a LIBRARY syncing up and using two other libraries
// im just happy Geckolib animations and PAL animations work together out of the box, just means i can use both at the same time

// gosh these comments are getting long, but id rather have a lot of comments in here than forgetting what i wanted to do
// TODO: properly plan this animation BS and implement it


// im not sure if i should keep this as an interface and instead turn it into a class that can be extended
// since this class here then could extend geo item and make it easier to register geckolib animations and build references between both geckolib and PAl
// ik a lot reads like bs here but thats bc im tired AF rn but i needed to get my thoughts and ideas down somehow, ts can be refined later
public interface AnimatableWeapon {
}
