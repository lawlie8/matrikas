insert into library (image_name, source, api_url,is_sidecar) values("ebs-csi-driver/aws-ebs-csi-driver", "public.ecr.aws/ebs-csi-driver/aws-ebs-csi-driver", "https://api.github.com/repos/kubernetes-sigs/aws-ebs-csi-driver/releases",false);
insert into library (image_name, source, api_url,is_sidecar) values("istio", "istio", "https://api.github.com/repos/istio/istio/releases",true);

insert into side_cars (lib_id, side_car_name, api_url) values(2, "pilot", "https://github.com/istio/istio");
insert into side_cars (lib_id, side_car_name, api_url) values(2, "proxyv2", "https://github.com/istio/istio");
insert into side_cars (lib_id, side_car_name, api_url) values(2, "install-cni", "https://github.com/istio/cni");
insert into side_cars (lib_id, side_car_name, api_url) values(2, "ztunnel", "https://github.com/istio/istio");