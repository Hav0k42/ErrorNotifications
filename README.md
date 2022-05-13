# ErrorNotifications
Spigot plugin that sends error messages to the desired email.


This plugin is intended to be used mainly by devs testing their code on a server that people are actively playing on. You might not be going about your day, so its nice to have a notification telling you something went wrong, and that there may be someone playing on your server throwing a fit about it.

# Usage

The way I'm intending this to be used is to send text messages from emails. It can be used to send emails regularly, but who actually checks their emails right?
To do this add a email to text using /addrecipient ##########@domain.domain.domain. I use AT&T so mine would look like ##########@mms.att.net. I'm not sure how other providers do this, but AT&T has two messaging systems mms and txt. Sending messages through txt will always arrive from a separate number, so if theres an option between mms and txt choose mms. Your provider may or may not require the country code for this to work. I recommend testing this from your email to make sure you have the right address, and that the notifications won't come from several different numbers.

Emails should arrive from spigoterrornotifications@gmail.com

Authentication issues may occur. I don't know how to help you with that :D.


# Commands:

toggle: enable or disable notifications.

addrecipient: add an email to the list

removerecipient: remove an email from the list
