#version 410 core

uniform vec4 Tint;
uniform sampler2D DefaultSampler;

in vec4 vertexColor;
in vec2 texCoord0;
out vec4 fragColor;

void main() {
    // bands mean UVs are arriving; the scale doesn't matter
    vec4 tex = texture(DefaultSampler, texCoord0);
    ivec2 size = textureSize(DefaultSampler, 0);
    fragColor = texelFetch(DefaultSampler, ivec2(texCoord0 * vec2(size)), 0) * Tint;
}