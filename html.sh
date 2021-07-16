#gradle html:dist
git rm -rf docs
cp -R html/build/dist docs
