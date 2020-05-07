#!/usr/bin/env bash

set -e

MESSAGE=`echo $1 | sed 's/yangyuan@gozap.com/😉龙贷最帅的人/g'`

if [ "$MESSAGE" == "" ]; then
    echo "Error: MESSAGE is blank..."
    exit 1
fi

curl -s -X POST -d "payload={
                            \"text\": \"**$MESSAGE**\",
                            \"username\": \"GitLab-CI\",
                            \"icon_url\": \"https://gitlab.longdai.com/assets/gitlab_logo-7ae504fe4f68fdebb3c2034e36621930cd36ea87924c11ff65dbcb8ed50dca58.png\"
                         }" \
    https://im.gozap.com/hooks/n8zpe94aypfc8gsdofrarfgc4e
