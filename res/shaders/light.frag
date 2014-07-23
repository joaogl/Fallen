uniform float lightInt;
uniform vec2 lightLocation;
uniform vec3 lightColor;

void main() {
	float distance = length(lightLocation - gl_FragCoord.xy);
	float attenuation = lightInt / distance;
	vec4 color = vec4(attenuation, attenuation, attenuation, pow(attenuation, 3) * 10) * vec4(lightColor * lightInt, 1);

	gl_FragColor = color;
}