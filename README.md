# ErrorNotifications
Spigot plugin that sends error messages to the desired email.


This plugin is intended to be used mainly by devs testing their code on a server that people are actively playing on. You might not be going about your day, so its nice to have a notification telling you something went wrong, and that there may be someone playing on your server throwing a fit about it.

# Usage
Because I'm lazy and don't really care about supporting this, I only added support to send emails from google accounts.

The way I'm intending this to be used is to send text messages from emails. It can be used to send emails regularly, but who actually checks their emails right?
To do this set the 'toEmail' value in the config to ##########@domain.domain.domain. I use AT&T so mine would look like ##########@mms.att.net. I'm not sure how other providers do this, but AT&T has two messaging systems mms and txt. Sending messages through txt will always arrive from a separate number, so if theres an option between mms and txt choose mms. Your provider may or may not require the country code for this to work, I recommend testing this from your email to make sure you have the right address.

You may run into some authentication issues. At the time of writing google is changing their settings for allowing unsecure apps to access your account. So, this plugin will work until may 30th at which point it will become obselete. yay.
To allow this plugin to sign into your google account, go to your account settings. Scroll to less secure app access, and turn it on. If I get enough use out of this, I may update it in the future to support OAUTH2. Right now thats too complicated for me to wrap my small brain around.
