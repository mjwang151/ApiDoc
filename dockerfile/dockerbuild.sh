#! /bin/bash

project_name=$1
projec_version=$2
base_dir=$3
echo "参数1:${project_name}"
echo "参数2:${projec_version}"
echo "参数3:${base_dir}"

image_name=$(echo ${project_name} | tr '[A-Z]' '[a-z]')
echo "参数projectname:${project.name}"
echo "参数projecversion:${project.version}"

echo "${base_dir}/dockerfile/Dockerfile"
sed -i "s/\${project.name}/${project_name}/g" ${base_dir}/dockerfile/Dockerfile
sed -i "s/\${project.version}/${projec_version}/g" ${base_dir}/dockerfile/Dockerfile

cp -r ${base_dir}/target/AmarPackTest.jar ${base_dir}/dockerfile
cp -r ${base_dir}/config ${base_dir}/dockerfile
cp -r ${base_dir}/logs ${base_dir}/dockerfile
cp -r ${base_dir}/*.sh ${base_dir}/dockerfile

cwd=`pwd`
echo "BUILD_ID=${BUILD_ID},cwd=${cwd}"
target='test'
dockerhub='192.168.61.93'
isproduct=`echo $cwd | grep "dw"`
if [ ! -z "$isproduct" ]; then
	target='dw'
	#dockerhub='172.20.3.82'
fi

docker_bin=`which docker`
if [ ! -z "$docker_bin" ]; then
	$docker_bin build -t ${dockerhub}/amarsoft/${image_name}:${BUILD_ID}${target} ${base_dir}/dockerfile
	#/bin/docker build -t 192.168.61.93/amarsoft/amarpacktest:1test /home/longer/jenkins/.jenkins/workspace/AmarPackTest/dockerfile	
	echo "$docker_bin build -t ${dockerhub}/amarsoft/${image_name}:${BUILD_ID}${target} ${base_dir}/dockerfile"
	echo "Image build success!"
	#/bin/docker push 192.168.61.93/amarsoft/amarpacktest:1test
	$docker_bin push ${dockerhub}/amarsoft/${image_name}:${BUILD_ID}${target}
	echo "$docker_bin push ${dockerhub}/amarsoft/${image_name}:${BUILD_ID}${target}"
	echo "Image push success!"
else
	echo "No docker installed!"
fi
