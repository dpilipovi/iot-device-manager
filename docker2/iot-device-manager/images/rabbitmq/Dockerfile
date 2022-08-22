FROM rabbitmq:3-management-alpine

RUN echo "management.load_definitions=/etc/rabbitmq/definitions.json" >> /etc/rabbitmq/rabbitmq.conf
ADD config/definitions.json /etc/rabbitmq/
