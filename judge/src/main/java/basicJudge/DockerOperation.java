package basicJudge;//package sustech.edu.phantom.dboj.basicJudge;
//
//
//import com.alibaba.fastjson.JSONObject;
//import com.github.dockerjava.api.DockerClient;
//import com.github.dockerjava.api.command.CreateContainerResponse;
//import com.github.dockerjava.api.model.ExposedPort;
//import com.github.dockerjava.api.model.Info;
//import com.github.dockerjava.api.model.PortBinding;
//import com.github.dockerjava.api.model.Ports;
//import com.github.dockerjava.core.DefaultDockerClientConfig;
////import com.github.dockerjava.core.DockerClientBuilder;
//import com.github.dockerjava.core.DockerClientConfig;
//import com.github.dockerjava.core.DockerClientImpl;
//import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
//import com.github.dockerjava.transport.DockerHttpClient;
//
//import static com.github.dockerjava.api.model.HostConfig.newHostConfig;
//
//public class DockerOperation {
//    public static void main(String[] args) {
//        DockerOperation op=new DockerOperation();
//        op.connectDocker();
//    }
//    /**
//     * 连接docker服务器
//     * @return
//     */
//    public DockerClient connectDocker(){
//        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
//                .withDockerHost("tcp://10.20.127.255:2375")
//                .withDockerTlsVerify(false)
////                .withDockerCertPath("/home/user/.docker")
////                .withRegistryUsername(registryUser)
////                .withRegistryPassword(registryPass)
////                .withRegistryEmail(registryMail)
////                .withRegistryUrl(registryUrl)
//                .build();
//        DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
//                .dockerHost(config.getDockerHost())
//                .sslConfig(config.getSSLConfig())
//                .build();
//        DockerClient dockerClient = DockerClientImpl.getInstance(config, httpClient);
//        Info info = dockerClient.infoCmd().exec();
//        String infoStr = JSONObject.toJSONString(info);
//        System.out.println("docker的环境信息如下：=================");
//        System.out.println(info);
////        CreateContainerResponse container1=dockerClient.createContainerCmd("database:1.0")
////                .withPortBindings(PortBinding.parse("12222:5432"))
////                .exec();
////        dockerClient.startContainerCmd(container1.getId()).exec();
//
//        return dockerClient;
//    }
//
//    /**
//     * 创建容器
//     * @param client
//     * @return
//     */
//    public CreateContainerResponse createContainers(DockerClient client,String containerName,String imageName){
//        //映射端口8088—>80
//        ExposedPort tcp80 = ExposedPort.tcp(80);
//        Ports portBindings = new Ports();
//        portBindings.bind(tcp80, Ports.Binding.bindPort(8088));
//
//        CreateContainerResponse container = client.createContainerCmd(imageName)
//                .withName(containerName)
//                .withHostConfig(newHostConfig().withPortBindings(portBindings))
//                .withExposedPorts(tcp80).exec();
//        return container;
//    }
//
//
//    /**
//     * 启动容器
//     * @param client
//     * @param containerId
//     */
//    public void startContainer(DockerClient client,String containerId){
//        client.startContainerCmd(containerId).exec();
//    }
//
//    /**
//     * 启动容器
//     * @param client
//     * @param containerId
//     */
//    public void stopContainer(DockerClient client,String containerId){
//        client.stopContainerCmd(containerId).exec();
//    }
//
//    /**
//     * 删除容器
//     * @param client
//     * @param containerId
//     */
//    public void removeContainer(DockerClient client,String containerId){
//        client.removeContainerCmd(containerId).exec();
//    }
//
//}
