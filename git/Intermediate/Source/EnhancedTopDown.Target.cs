using UnrealBuildTool;

public class EnhancedTopDownTarget : TargetRules
{
	public EnhancedTopDownTarget(TargetInfo Target) : base(Target)
	{
		DefaultBuildSettings = BuildSettingsVersion.V2;
		Type = TargetType.Game;
		ExtraModuleNames.Add("EnhancedTopDown");
	}
}
