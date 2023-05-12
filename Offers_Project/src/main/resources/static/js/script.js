var MessageViewModel = function() {
	// Make the self as 'this' reference
	var self = this;
	// Declare observable which will be bind with UI
	self.id = ko.observable("0");
	self.subject = ko.observable("");
	self.text = ko.observable("");
	self.senderId = ko.observable("");
	self.receiverId = ko.observable("");
	self.sender = ko.observable("");
	self.receiver = ko.observable("");

	// The Object which stored data entered in the observables
	var MessageData = {
		id : self.id,
		subject : self.subject,
		text : self.text,
		senderId : senderId,
		receiverId : receiverId,
		sender : sender,
		receiver : receiver,

	};
	// Declare an ObservableArray for Storing the JSON Response
	self.Messages = ko.observableArray([]);
	self.AuthUserMessages = ko.observableArray([]);

	self.save = function() {
		// Ajax call to Insert the Customer record
		if (validateForm()) {
			$.ajax({
				type : "POST",
				url : "http://localhost:8080/saveMessage/" + senderId + "/"
						+ receiverId,
				data : ko.toJSON(MessageData), // Convert the Observable Data
				// into JSON
				beforeSend : function(xhr) {
					xhr.overrideMimeType("text/plain; charset=x-user-defined");
				},
				contentType : "application/json",
				success : function(data) {
					alert("Record Added Successfully");
					self.id(data.id);
					$(".formtable").remove();
					$("h2").replaceWith("<h1>Message Sent</h1");
					var link = $("<a />");
					link.attr("href", "/");
					link.html("Go To Home");

					$("#redirectButton").append(link);
				},
				error : function() {

				}
			});// Ends Here

		}
		;
	};

	self.reply = function() {
		if (validateForm()) {
			$.ajax({
				type : "POST",
				url : "http://localhost:8080/replyMessage/" + senderId + "/"
						+ receiverId,
				data : ko.toJSON(MessageData), // Convert the Observable Data
												// into
				// JSON
				beforeSend : function(xhr) {
					xhr.overrideMimeType("text/plain; charset=x-user-defined");
				},
				contentType : "application/json",
				success : function(data) {
					alert("Record Added Successfully");
					self.id(data.id);
					$(".formtable").remove();
					$("h2").replaceWith("<h1>Reply Sent</h1");

				},
				error : function() {
					$(".formtable").remove();
					$("h2").replaceWith("<h1>Reply Failed</h1");
				}
			});
		}
		;
	};

	// Function to Display record to be updated
	self.getselectedmessages = function(message) {
		self.id(message.id), self.subject(message.subject), self
				.text(message.text), self.senderId(message.senderId), self
				.receiverId(message.receiverId), self
				.receiver(message.receiver), self.sender(message.sender)

	};
};
ko.applyBindings(new MessageViewModel());