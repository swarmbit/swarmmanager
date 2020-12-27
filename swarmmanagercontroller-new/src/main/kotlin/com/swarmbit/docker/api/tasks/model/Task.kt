package com.swarmbit.docker.api.tasks.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.swarmbit.docker.api.common.annotation.DockerRemoteApiMinVersion
import com.swarmbit.docker.api.common.model.*
import com.swarmbit.docker.api.configs.model.ConfigReference
import com.swarmbit.docker.api.networks.model.NetworkAttachmentConfig
import com.swarmbit.docker.api.nodes.model.Platform
import com.swarmbit.docker.api.secrets.model.SecretReference

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Task(
        @JsonProperty("ID")
        val id: String? = null,
        @JsonProperty("Version")
        val version: Version? = null,
        @JsonProperty("Name")
        val name: String? = null,
        @JsonProperty("Labels")
        val labels: Map<String, String>? = null,
        @JsonProperty("Spec")
        val spec: TaskSpec? = null,
        @JsonProperty("ServiceID")
        val serviceId: String? = null,
        @JsonProperty("Slot")
        val slot: Long? = null,
        @JsonProperty("NodeID")
        val nodeId: String? = null,
        @JsonProperty("Status")
        val status: TaskStatus? = null,
        @JsonProperty("DesiredState")
        val desiredState: String? = null,
        @JsonProperty("NetworksAttachments")
        val networksAttachments: List<NetworkAttachmentConfig>? = null,
        @DockerRemoteApiMinVersion("v1.41")
        @JsonProperty("JobIteration")
        val jobIteration: Version? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class TaskStatus(
        @JsonProperty("Timestamp")
        val timestamp: String? = null,
        @JsonProperty("State")
        val taskState: String? = null,
        @JsonProperty("Message")
        val message: String? = null,
        @JsonProperty("Err")
        val err: String? = null,
        @JsonProperty("ContainerStatus")
        val containerStatus: ContainerStatus? = null,
        @JsonProperty("PortStatus")
        val portStatus: PortStatus? = null
)


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class TaskSpec(
        @JsonProperty("Resources")
        val resources: ResourceRequirements? = null,
        @JsonProperty("RestartPolicy")
        val restartPolicy: RestartPolicy? = null,
        @JsonProperty("Placement")
        val placement: Placement? = null,
        @JsonProperty("Networks")
        val networks: List<NetworkAttachmentConfig>? = null,
        @JsonProperty("LogDriver")
        val logDriver: Driver? = null,
        @JsonProperty("ForceUpdate")
        val forceUpdate: Long? = null,
        @JsonProperty("Runtime")
        val runtime: String? = null,
        @JsonProperty("ContainerSpec")
        val containerSpec: ContainerSpec? = null,
        @DockerRemoteApiMinVersion("v1.39")
        @JsonProperty("NetworkAttachmentSpec")
        val networkAttachmentSpec: NetworkAttachmentSpec? = null,
        @JsonProperty("PluginSpec")
        val pluginSpec: PluginSpec? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class PortStatus(
        @JsonProperty("Ports")
        val ports: List<PortConfig>? = null
)

@DockerRemoteApiMinVersion("v1.38")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class NetworkAttachmentSpec(
        @JsonProperty("ContainerID")
        val containerId: String? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ContainerSpec(
        @JsonProperty("Image")
        val image: String? = null,
        @JsonProperty("Labels")
        val labels: Map<String, String>? = null,
        @JsonProperty("Command")
        val command: List<String>? = null,
        @JsonProperty("Args")
        val args: List<String>? = null,
        @JsonProperty("Hostname")
        val hostname: String? = null,
        @JsonProperty("Env")
        val env: List<String>? = null,
        @JsonProperty("Dir")
        val dir: String? = null,
        @JsonProperty("Registry")
        val user: String? = null,
        @JsonProperty("Groups")
        val groups: List<String>? = null,
        @JsonProperty("TTY")
        val isTty: Boolean? = null,
        @JsonProperty("OpenStdin")
        val isOpenStdin: Boolean? = null,
        @JsonProperty("Mounts")
        val mounts: List<Mount>? = null,
        @JsonProperty("StopGracePeriod")
        val stopGracePeriod: Long? = null,
        @JsonProperty("StopSignal")
        val stopSignal: String? = null,
        @JsonProperty("Healthcheck")
        val healthConfig: HealthConfig? = null,
        @JsonProperty("Hosts")
        val hosts: List<String>? = null,
        @JsonProperty("DNSConfig")
        val dnsConfig: DNSConfig? = null,
        @JsonProperty("Secrets")
        val secrets: List<SecretReference>? = null,
        @JsonProperty("Configs")
        val configs: List<ConfigReference>? = null,
        @JsonProperty("ReadOnly")
        val readOnly: Boolean? = null,
        @JsonProperty("Isolation")
        val isolation: String? = null,
        @DockerRemoteApiMinVersion("v1.40")
        @JsonProperty("Sysctls")
        val sysctls: Map<String, String>? = null,
        @DockerRemoteApiMinVersion("v1.40")
        @JsonProperty("Privileges")
        val privileges: Privileges? = null,
        @DockerRemoteApiMinVersion("v1.41")
        @JsonProperty("CapabilityAdd")
        val capabilityAdd: List<String>? = null,
        @DockerRemoteApiMinVersion("v1.41")
        @JsonProperty("CapabilityDrop")
        val capabilityDrop: List<String>? = null,
        @DockerRemoteApiMinVersion("v1.41")
        @JsonProperty("Ulimits")
        val ulimits: List<Ulimit>? = null
)

@DockerRemoteApiMinVersion("v1.41")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Ulimit(
        @JsonProperty("Name")
        val name: String? = null,
        @JsonProperty("Hard")
        val hard: Long? = null,
        @JsonProperty("Soft")
        val soft: Long? = null
)
@DockerRemoteApiMinVersion("v1.40")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Privileges(
        @JsonProperty("CredentialSpec")
        val credentialSpec: CredentialSpec? = null,
        @JsonProperty("SELinuxContext")
        val seLinuxContext: SELinuxContext? = null
)

@DockerRemoteApiMinVersion("v1.40")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class CredentialSpec(
        @JsonProperty("Config")
        val config: String? = null,
        @JsonProperty("File")
        val file: String? = null,
        @JsonProperty("Registry")
        val registry: String? = null,
)

@DockerRemoteApiMinVersion("v1.39")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class SELinuxContext(
        @JsonProperty("Disable")
        val disable: Boolean? = null,
        @JsonProperty("User")
        val user: String? = null,
        @JsonProperty("Role")
        val role: String? = null,
        @JsonProperty("Type")
        val type: String? = null,
        @JsonProperty("Level")
        val level: String? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ContainerStatus(
        @JsonProperty("ContainerID")
        val containerId: String? = null,
        @JsonProperty("PID")
        val pid: Int? = null,
        @JsonProperty("ExitCode")
        val exitCode: Int? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class HealthConfig(
        @JsonProperty("Test")
        val test: List<String>? = null,
        @JsonProperty("Interval")
        val interval: Long? = null,
        @JsonProperty("Timeout")
        val timeout: Long? = null,
        @JsonProperty("Retries")
        val retries: Int? = null,
        @JsonProperty("StartPeriod")
        val startPeriod: Long? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class DNSConfig(
        @JsonProperty("Nameservers")
        val nameServers: List<String>? = null,
        @JsonProperty("Search")
        val search: List<String>? = null,
        @JsonProperty("Options")
        val options: List<String>? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Mount(
        @JsonProperty("Type")
        val type: String? = null,
        @JsonProperty("Source")
        val source: String? = null,
        @JsonProperty("Target")
        val target: String? = null,
        @JsonProperty("ReadOnly")
        val isReadOnly: Boolean? = null,
        @JsonProperty("BindOptions")
        val bindOptions: BindOptions? = null,
        @JsonProperty("VolumeOptions")
        val volumeOptions: VolumeOptions? = null,
        @JsonProperty("TmpfsOptions")
        val tmpfsOptions: TmpfsOptions? = null,
        @JsonProperty("Consistency")
        val consistency: String? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class BindOptions(
        @JsonProperty("Propagation")
        val propagation: String? = null,
        @DockerRemoteApiMinVersion("v1.41")
        @JsonProperty("NonRecursive")
        val nonRecursive: Boolean? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class VolumeOptions(
        @JsonProperty("NoCopy")
        val isNoCopy: Boolean? = null,
        @JsonProperty("Labels")
        val labels: Map<String, String>? = null,
        @JsonProperty("DriverConfig")
        val driver: Driver? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class TmpfsOptions(
        @JsonProperty("SizeBytes")
        val sizeBytes: Long? = null,
        @JsonProperty("Mode")
        val mode: Int? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class PluginSpec(
        @JsonProperty("Name")
        val name: String? = null,
        @JsonProperty("Remote")
        val remote: String? = null,
        @JsonProperty("Disabled")
        val isDisabled: Boolean? = false,
        @JsonProperty("PluginPrivilege")
        val pluginPrivilege: PluginPrivilege? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class PluginPrivilege(
        @JsonProperty("Name")
        val name: String? = null,
        @JsonProperty("Description")
        val description: String? = null,
        @JsonProperty("Value")
        val value: List<String>? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Placement(
        @JsonProperty("Constraints")
        val constraints: List<String>? = null,
        @JsonProperty("Preferences")
        val preferences: List<PlacementPreference>? = null,
        @JsonProperty("Platforms")
        val platforms: List<Platform>? = null,
        @DockerRemoteApiMinVersion("v1.40")
        @JsonProperty("MaxReplicas")
        val maxReplicas: Long? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class PlacementPreference(
        @JsonProperty("Spread")
        val spread: SpreadOver? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class SpreadOver(
        @JsonProperty("SpreadDescriptor")
        val spreadDescriptor: String? = null
)
