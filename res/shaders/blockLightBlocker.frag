#version 330 compatibility

uniform sampler2D texture;
in vec2 texCoords;

uniform float dayLight;

uniform vec2 lightPosition[50];
uniform vec3 lightColor[50];
uniform float lightIntensity[50];
uniform float lightInUse[50];
uniform float lightType[50];
uniform float lightSize[50];
uniform float lightFacing[50];

void main() {	
	vec4 color = vec4(1.0, 1.0, 1.0, 1.0);
	vec4 tex = texture2D(texture, gl_TexCoord[0].st);
	color = tex;
		
	float alpha = 1.0;
	if (tex.x == 1.0 && tex.y == 0.0 && tex.z == 1.0) {
		alpha = 0.0;
	}
	
	vec4 result = vec4(color.xyz, alpha);
	color = result;
	 
	int num = 0;
	int changed = 0;	
	for (int i = 0; i < 50; i++) {
		if (lightInUse[i] == 1) {
			vec2 pos = lightPosition[i];
			float distance = length(pos - gl_FragCoord.xy);
			if (pos.x == 0 && pos.y == 0) continue;
			vec3 col = lightColor[i];
			float ints = lightIntensity[i];
			
			float attenuation = 1.0 / distance;
			
			float falloff = 80;
			falloff -= distance / 25.0f / ints;						
				
			if (attenuation >= 0.018) {
				if (lightType[i] == 1) {
					color *= vec4(attenuation, attenuation, attenuation, pow(attenuation, 3)) * vec4((col / distance * 15) * ints, 1.0) + 0.01;
				
					color /= (distance / (ints * falloff));
					changed = 1;
					num++;
				} else if (lightType[i] == 3) {
					float angle = degrees(acos((pos.x - gl_FragCoord.x) / distance));		
					if (pos.y < gl_FragCoord.y) angle = 360 - angle;
					
					float max = (lightFacing[i] + (lightSize[i] / 2));
					float min = (lightFacing[i] - (lightSize[i] / 2));	
					bool spec = false;
					
					if (max > 360) {
						max = max - 360;
						spec = true;
					}
					if ((lightFacing[i] - (lightSize[i] / 2)) < 0) {
						min = 360 - abs(lightFacing[i] - (lightSize[i] / 2));
						spec = true;
					}
			
					if ((angle >= min && angle <= max && !spec) || ((angle >= min || angle <= max) && spec)) {
						color *= vec4(attenuation, attenuation, attenuation, pow(attenuation, 3)) * vec4((col / distance * 15) * ints, 1.0) + 0.01;
					
						color /= (distance / (ints * falloff));
						changed = 1;
						num++;
					}
				}
			}
		}
	}
	if (changed == 1) color *= 30.0 * pow(166.0, float(num - 1)) + 1;
	else color = vec4(color * vec4(dayLight, dayLight, dayLight, dayLight));	
	
	if (min(result, color) == color) gl_FragColor = color;
	else gl_FragColor = result; 
}