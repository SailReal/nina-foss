package org.cryptomator.data.cloud.pcloud;

import org.cryptomator.domain.CloudNode;

interface PCloudNode extends PCloudIdCloudNode {

	@Override
	Long getId();

	@Override
	PCloudFolder getParent();

}
