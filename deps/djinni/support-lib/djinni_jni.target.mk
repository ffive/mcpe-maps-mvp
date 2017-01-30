# This file is generated by gyp; do not edit.

include $(CLEAR_VARS)

LOCAL_MODULE_CLASS := STATIC_LIBRARIES
LOCAL_MODULE := deps_djinni_support_lib_djinni_jni_gyp
LOCAL_MODULE_SUFFIX := .a
LOCAL_MODULE_TARGET_ARCH := $(TARGET_$(GYP_VAR_PREFIX)ARCH)
LOCAL_SDK_VERSION := 19
gyp_intermediate_dir := $(call local-intermediates-dir,,$(GYP_VAR_PREFIX))
gyp_shared_intermediate_dir := $(call intermediates-dir-for,GYP,shared,,,$(GYP_VAR_PREFIX))

# Make sure our deps are built first.
GYP_TARGET_DEPENDENCIES :=

GYP_GENERATED_OUTPUTS :=

# Make sure our deps and generated files are built first.
LOCAL_ADDITIONAL_DEPENDENCIES := $(GYP_TARGET_DEPENDENCIES) $(GYP_GENERATED_OUTPUTS)

LOCAL_GENERATED_SOURCES :=

GYP_COPIED_SOURCE_ORIGIN_DIRS :=

LOCAL_SRC_FILES := \
	deps/djinni/support-lib/jni/djinni_support.cpp


# Flags passed to both C and C++ files.
MY_CFLAGS_Debug := \
	-Wall \
	-Wextra \
	-Werror \
	-fvisibility=hidden \
	-g \
	-O0

MY_DEFS_Debug := \
	'-DDEBUG'


# Include paths placed before CFLAGS/CPPFLAGS
LOCAL_C_INCLUDES_Debug := \
	$(LOCAL_PATH)/deps/djinni/support-lib/jni


# Flags passed to only C++ (and not C) files.
LOCAL_CPPFLAGS_Debug := \
	-Wall \
	-Wextra \
	-Werror \
	-fvisibility=hidden \
	-std=c++1y \
	-fexceptions \
	-frtti


# Flags passed to both C and C++ files.
MY_CFLAGS_Release := \
	-Wall \
	-Wextra \
	-Werror \
	-fvisibility=hidden \
	-Os \
	-fomit-frame-pointer \
	-fdata-sections \
	-ffunction-sections

MY_DEFS_Release := \
	'-DNDEBUG'


# Include paths placed before CFLAGS/CPPFLAGS
LOCAL_C_INCLUDES_Release := \
	$(LOCAL_PATH)/deps/djinni/support-lib/jni


# Flags passed to only C++ (and not C) files.
LOCAL_CPPFLAGS_Release := \
	-Wall \
	-Wextra \
	-Werror \
	-fvisibility=hidden \
	-std=c++1y \
	-fexceptions \
	-frtti


LOCAL_CFLAGS := $(MY_CFLAGS_$(GYP_CONFIGURATION)) $(MY_DEFS_$(GYP_CONFIGURATION))
LOCAL_C_INCLUDES := $(GYP_COPIED_SOURCE_ORIGIN_DIRS) $(LOCAL_C_INCLUDES_$(GYP_CONFIGURATION))
LOCAL_CPPFLAGS := $(LOCAL_CPPFLAGS_$(GYP_CONFIGURATION))
LOCAL_ASFLAGS := $(LOCAL_CFLAGS)
### Rules for final target.
# Add target alias to "gyp_all_modules" target.
.PHONY: gyp_all_modules
gyp_all_modules: deps_djinni_support_lib_djinni_jni_gyp

# Alias gyp target name.
.PHONY: djinni_jni
djinni_jni: deps_djinni_support_lib_djinni_jni_gyp

include $(BUILD_STATIC_LIBRARY)
