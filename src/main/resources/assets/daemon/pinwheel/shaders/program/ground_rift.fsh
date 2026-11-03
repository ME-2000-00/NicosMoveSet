#include veil:space_helper

uniform sampler2D DiffuseSampler0;
uniform sampler2D DiffuseDepthSampler;

uniform vec3 CameraPos;     // world-space camera position, set from Java
uniform vec3 ScanOrigin;
uniform float ScanRadius;
uniform float fade;

in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec4 scene  = texture(DiffuseSampler0, texCoord);
    float depth = texture(DiffuseDepthSampler, texCoord).r;

    vec3 result = scene.rgb;

    if (depth < 1.0) {   // not sky
        // camera-relative, so add the camera position to get world space
        vec2 wp = screenToLocalSpace(texCoord, depth).xz + CameraPos.xz;
        float dist = distance(wp, ScanOrigin.xz);

        float front  = 1.0 - step(0.0, abs(dist - ScanRadius));
        float behind = step(dist, ScanRadius) * exp(-(ScanRadius - dist) * fade);

        float mask = max(front, behind);
        result = mix(scene.rgb, vec3(0.2, 1.0, 0.8), clamp(0.0,0.8, mask));
    }

    fragColor = vec4(result, 1.0);
}