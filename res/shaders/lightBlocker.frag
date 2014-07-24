#version 330 compatibility

uniform sampler2D texture;
in vec2 texCoords;

uniform float dayLight;

uniform vec2 lightPosition[50];
uniform vec3 lightColor[50];
uniform float lightIntensity[50];
uniform float lightInUse[50];

void main() {	
	vec4 color = vec4(1.0, 1.0, 1.0, 1.0);
	vec4 tex = texture2D(texture, gl_TexCoord[0].st);
	color = tex;
	
	color = vec4(color * vec4(dayLight, dayLight, dayLight, dayLight));	
	
	int num = 0;	
	for (int i = 0; i < 50; i++) {
		if (lightInUse[i] == 1) {
			vec2 pos = lightPosition[i];
			float distance = length(pos - gl_FragCoord.xy);
			if (pos.x == 0 && pos.y == 0) continue;
			num++;
			vec3 col = lightColor[i];
			float ints = lightIntensity[i];
			
			float distance = length(pos - gl_FragCoord.xy);
			float attenuation = 1.0 / distance;
			
			float falloff = 80;
			falloff -= distance / 25.0f / ints ;		
			
			color *= vec4(attenuation, attenuation, attenuation, pow(attenuation, 3)) * vec4((col / distance * 15) * ints, 1.0) + 0.01;
			
			color /= (distance / (ints * falloff));
		}
	}
	color *= 30.0 * pow(166.0, float(num - 1)) + 1;
		
	gl_FragColor = color; 
}