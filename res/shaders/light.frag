uniform float lightInt;
uniform vec2 lightLocation;
uniform vec3 lightColor;
uniform float lightType;
uniform float lightCenter;
uniform float lightSize;
uniform float lightFacing;

void main() {
	float distance = length(lightLocation - gl_FragCoord.xy);
	float attenuation = lightInt / distance;
	vec4 color = vec4(0, 0, 0, 0);
	
	if (lightType == 1) {	
		if (lightCenter == 0 && distance < 30) {
			distance = 30;				
			attenuation = lightInt / distance;
			color = vec4(attenuation, attenuation, attenuation, pow(attenuation, 3) * 10) * vec4(lightColor * lightInt, 1);		
		} else {
			attenuation = lightInt / distance;
			color = vec4(attenuation, attenuation, attenuation, pow(attenuation, 3) * 10) * vec4(lightColor * lightInt, 1);		
		}
	} else if (lightType == 2) {	
		vec2 p1 = vec2((cos(lightFacing) * 5) + lightLocation.x, (sin(lightFacing) * 5) + lightLocation.y);
		vec2 p2 = vec2((cos(lightFacing) * 5) - lightLocation.x, (sin(lightFacing) * 5) - lightLocation.y);
		vec2 pm = vec2(lightLocation.x + (cos(45) * 1000), lightLocation.y + (cos(45) * 1000));
		vec2 p3 = vec2((cos(lightFacing) * 5) + pm.x, (sin(lightFacing) * 5) + pm.y);
		vec2 p4 = vec2((cos(lightFacing) * 5) - pm.x, (sin(lightFacing) * 5) - pm.y);
		
		float a1 = pow(length(p1 - p2) * length(p1 - gl_FragCoord.xy), -2);
		float a2 = pow(length(p1 - p3) * length(p3 - gl_FragCoord.xy), -2);
		float a3 = pow(length(p3 - p4) * length(p4 - gl_FragCoord.xy), -2);
		float a4 = pow(length(p4 - p2) * length(p2 - gl_FragCoord.xy), -2);
		
		float a = length(p1 - p2) * length(p1 - p3);
	
		if (a = a1 + a2 + a3 + a4) {
			color = vec4(attenuation, attenuation, attenuation, pow(attenuation, 3) * 10) * vec4(lightColor * lightInt, 1);
		}		
	}  else if (lightType == 3) {	
		float angle = degrees(acos((lightLocation.x - gl_FragCoord.x) / distance));		
		if (lightLocation.y < gl_FragCoord.y) angle = 360 - angle;
		
		float max = (lightFacing + (lightSize / 2));
		float min = (lightFacing - (lightSize / 2));	
		bool spec = false;
		
		if (max > 360) {
			max = max - 360;
			spec = true;
		}
		if ((lightFacing - (lightSize / 2)) < 0) {
			min = 360 - abs(lightFacing - (lightSize / 2));
			spec = true;
		}

		if ((angle >= min && angle <= max && !spec) || ((angle >= min || angle <= max) && spec)) color = vec4(attenuation, attenuation, attenuation, pow(attenuation, 3) * 10) * vec4(lightColor * lightInt, 1);			
	} 
	gl_FragColor = color;
}