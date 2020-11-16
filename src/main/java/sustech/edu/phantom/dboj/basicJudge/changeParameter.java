package sustech.edu.phantom.dboj.basicJudge;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;

public class changeParameter {
    public static void main(String[] args) {
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().withDockerTlsVerify(false)
                .withDockerHost("tcp://10.20.87.51:2375")
                .build();
        DockerClient docker = DockerClientBuilder.getInstance(config).build();
        CreateContainerResponse response=docker.createContainerCmd("hello-world").exec();
        Info info = docker.infoCmd().exec();
        //System.out.print(info);
        System.out.println(response);

    }

}
