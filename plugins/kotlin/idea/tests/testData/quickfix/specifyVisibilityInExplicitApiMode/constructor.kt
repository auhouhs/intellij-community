// "Make '<init>' public explicitly" "true"
// COMPILER_ARGUMENTS: -Xexplicit-api=strict

public class Foo5 {
    constructor<caret>() {}
}


// FUS_QUICKFIX_NAME: org.jetbrains.kotlin.idea.quickfix.ChangeVisibilityFix$ChangeToPublicExplicitlyFix
// FUS_K2_QUICKFIX_NAME: org.jetbrains.kotlin.idea.quickfix.fixes.ChangeVisibilityFixFactories$getForcedApplicator$1