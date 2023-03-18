FROM mariadb:10.6
ENV TZ=Asiz/Seoul
RUN apt-get update && apt-get install -y tzdata && \
	echo $TZ > /etc/timezone && \
	ln -snf /usr/sahre/zoneinfo/$TZ /etc/localtime
